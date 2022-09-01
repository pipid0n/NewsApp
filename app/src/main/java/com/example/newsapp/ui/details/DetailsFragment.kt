package com.example.newsapp.ui.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.net.URL

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val mBinding get() = _binding!!
    private val bundleArgs: DetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleArg = bundleArgs.article

        articleArg.let {article ->
            article.urlToImage?.let {
                Glide.with(this).load(article.urlToImage).into(mBinding.ivHeader)
            }
            mBinding.ivHeader.clipToOutline = true
            mBinding.tvArticleDetailsTitle.text = article.title
            mBinding.tvArticleDetailsDescriptionText.text = article.description

            mBinding.btnArticleDetails.setOnClickListener{
                try {
                    Intent()
                        .setAction(Intent.ACTION_VIEW)
                        .addCategory(Intent.CATEGORY_BROWSABLE)
                        .setData(Uri.parse(takeIf { URLUtil.isValidUrl(article.url) }
                            ?.let {
                            article.url
                            }?: "http://google.com" ))
                            .let {
                                ContextCompat.startActivity(requireContext(), it, null)
                            }
                } catch (e: Exception) {
                    Toast.makeText(context, "The device doesn't have any browser to view the article!", Toast.LENGTH_LONG).show()
                }
            }

            mBinding.ivDetailsIconFavorite.setOnClickListener{
                viewModel.saveFavoriteArticle(article)

            }
        }
    }

}